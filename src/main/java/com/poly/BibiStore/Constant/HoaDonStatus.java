package com.poly.BibiStore.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public enum  HoaDonStatus {
    PENDING, PREPARED, SHIPPING, COMPLETED, RETURNED, CANCELLED;

}
