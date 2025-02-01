package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.CouponNotValidException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.Coupon;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.repository.CouponRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);
        Cart cart = cartRepository.findByUserId(user.getId());

        if(coupon == null) {
            throw new CouponNotValidException("Coupon not find");
        }

        if(user.getUsedCoupons().contains(coupon)){
            throw new CouponNotValidException("Coupon already used");
        }

        if(orderValue <= coupon.getMinimumOrderValue()){
            throw new CouponNotValidException("Valid for minimum order value  " + coupon);
        }

        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())){
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice = Math.round((cart.getTotalSellingPrice() * coupon.getDiscountPercentage()) / 100);
            cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountedPrice);
            cart.setCouponCode(code);
            cart.setCouponPrice((int) discountedPrice);
            return cartRepository.save(cart);
        }
        throw new CouponNotValidException("Coupon not valid");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);
        if(coupon == null){
            throw new Exception("Coupon NOT Found");
        }

        user.getUsedCoupons().remove(coupon);

        Cart cart = cartRepository.findByUserId(user.getId());
        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + cart.getCouponPrice());
        cart.setCouponCode(null);
        cart.setCouponPrice(0);
        return cartRepository.save(cart);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN")
    public void deleteCoupon(Long couponId) {
       couponRepository.deleteById(couponId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getCouponById(Long couponId) throws CouponNotValidException {

        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if(coupon.isPresent()){
            return coupon.get();
        }
        throw new CouponNotValidException("Coupon not found");
    }
}
