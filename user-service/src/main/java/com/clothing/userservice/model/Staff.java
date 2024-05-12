package com.clothing.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Table(name = "staff")
public class Staff extends User{
    public Staff(User user){
        super(user);
    }
}
