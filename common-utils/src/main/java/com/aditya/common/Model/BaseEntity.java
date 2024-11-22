package com.aditya.common.Model;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    private Date createdAt;

    private Date updatedAt;
}
