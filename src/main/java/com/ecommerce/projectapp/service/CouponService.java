package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.Coupon;
import com.ecommerce.projectapp.model.User;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    
    Cart applyCoupon(String code, double orderValue, User user) throws Exception;

    Cart removeCoupon(String code, User user) throws Exception;

    Coupon createCoupon(Coupon coupon);

    void deleteCoupon(Long couponId);

    List<Coupon> getAllCoupons();
    
    Coupon getCouponById(Long couponId);
}