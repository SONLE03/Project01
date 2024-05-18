package com.es.productService.service;

import com.es.productService.constant.APIStatus;
import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.*;
import com.es.productService.exception.BusinessException;
import com.es.productService.model.Image;
import com.es.productService.model.ProductEntity;
import com.es.productService.model.ProductStatus;
import com.es.productService.repository.ImageRepository;
import com.es.productService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    @Override
    public List<ProductImageResponse> getAllProducts() {
        List<ProductImageResponse> productImageResponses = new ArrayList<>();
        List<ProductResponse> productResponse = productRepository.findAllProducts();
        for (ProductResponse product : productResponse){
            List<ImageResponse> imageResponses = imageRepository.findImagesByProductId(product.getId());
            productImageResponses.add(new ProductImageResponse(product, imageResponses));
        }
        return productImageResponses;
    }
    @Override
    @Transactional
    public ProductImageResponse getDetailProduct(UUID productId) {
        if(!productRepository.existsById(productId))
            throw new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
        ProductResponse productResponse = productRepository.findProductById(productId);
        List<ImageResponse> imageResponses = imageRepository.findImagesByProductId(productId);
        return new ProductImageResponse(productResponse, imageResponses);
    }

    @Override
    public ProductToOrder getProductToOrder(UUID productId) {
        if(!productRepository.existsById(productId))
            throw new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
        return productRepository.getProductToOrder(productId);
    }

    @Override
    @Transactional
    public void createProduct(List<MultipartFile> multipartFiles, ProductRequest request) {
        CategoryResponse categoryResponse = categoryService.getCategory(request.getCategory());
        if(categoryResponse == null){
            throw  new BusinessException(APIStatus.CATEGORY_NOT_FOUND);
        }
        ProductEntity product = ProductEntity.builder()
                .product_Name(request.getProduct_Name())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(categoryResponse.getId())
                .productStatus(ProductStatus.convertIntegerToProductStatus(request.getStatus()))
                .build();
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        product.setCreatedAt(time);
        product.setUpdatedAt(time);
        productRepository.save(product);
        ExecutorService executorService = Executors.newFixedThreadPool(multipartFiles.size());
        List<Future<Map>> futures = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            Future<Map> future = executorService.submit(() -> {
                BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
                if (bi == null) {
                    throw new BusinessException(APIStatus.IMAGE_NOT_FOUND);
                }
                return cloudinaryService.upload(multipartFile, "products");
            });
            futures.add(future);
        }

        for (int i = 0; i < multipartFiles.size(); i++) {
            Future<Map> future = futures.get(i);
            try {
                Map result = future.get();
                imageRepository.save(
                        Image.builder()
                                .name((String) result.get("original_filename"))
                                .url((String) result.get("url"))
                                .product(product)
                                .cloudinaryId((String) result.get("public_id"))
                                .build()
                );
            } catch (Exception e) {
                // Xử lý ngoại lệ khi có lỗi xảy ra trong tiến trình tải lên ảnh
            }
        }
        executorService.shutdown();
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) throws IOException {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                () ->  new BusinessException(APIStatus.PRODUCT_NOT_FOUND));
        productRepository.deleteById(productId);
        List<Image> imageList = imageRepository.getImageByProduct(product);
        for(Image image : imageList){
            cloudinaryService.delete(image.getCloudinaryId());
        }
        imageRepository.deleteByProduct(product);
    }

    @Override
    @Transactional
    public void updateProduct(UUID productId, List<MultipartFile> multipartFiles, ProductRequest request) throws IOException {
        if(!productRepository.existsById(productId))
            new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
        CategoryResponse categoryResponse = categoryService.getCategory(request.getCategory());
        if(categoryResponse == null){
            throw  new BusinessException(APIStatus.CATEGORY_NOT_FOUND);
        }
        ProductEntity product = productRepository.getById(productId);
            product.setProduct_Name(request.getProduct_Name());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setCategory(categoryResponse.getId());
            product.setProductStatus(ProductStatus.convertIntegerToProductStatus(request.getStatus()));

        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        product.setUpdatedAt(time);
        productRepository.save(product);

        List<Image> imageList = imageRepository.getImageByProduct(product);
        for(Image image : imageList){
            cloudinaryService.delete(image.getCloudinaryId());
        }
        imageRepository.deleteByProduct(product);
        ExecutorService executorService = Executors.newFixedThreadPool(multipartFiles.size());
        List<Future<Map>> futures = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            Future<Map> future = executorService.submit(() -> {
                BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
                if (bi == null) {
                    throw new BusinessException(APIStatus.IMAGE_NOT_FOUND);
                }
                return cloudinaryService.upload(multipartFile, "products");
            });
            futures.add(future);
        }

        for (int i = 0; i < multipartFiles.size(); i++) {
            Future<Map> future = futures.get(i);
            try {
                Map result = future.get();
                imageRepository.save(
                        Image.builder()
                                .name((String) result.get("original_filename"))
                                .url((String) result.get("url"))
                                .product(product)
                                .cloudinaryId((String) result.get("public_id"))
                                .build()
                );
            } catch (Exception e) {
                // Xử lý ngoại lệ khi có lỗi xảy ra trong tiến trình tải lên ảnh
            }
        }
        executorService.shutdown();
    }
    @Override
    @Transactional
    public void updateQuantity(List<ProductEventResponse> productList){
        List<ProductEntity> productsToUpdate = new ArrayList<>();

        for (ProductEventResponse item : productList) {
            ProductEntity product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BusinessException(APIStatus.PRODUCT_NOT_FOUND));

            product.setQuantity(product.getQuantity() + item.getQuantity());
            productsToUpdate.add(product);
        }

        if (!productsToUpdate.isEmpty()) {
            productRepository.saveAll(productsToUpdate);
        }
    }
}
