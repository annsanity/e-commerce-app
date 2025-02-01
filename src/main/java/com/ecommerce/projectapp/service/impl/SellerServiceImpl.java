package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.config.JwtProvider;
import com.ecommerce.projectapp.domain.AccountStatus;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.repository.AddressRepository;
import com.ecommerce.projectapp.repository.SellerRepository;
import com.ecommerce.projectapp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.JavaServiceLoadable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService  {

    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public Seller getSellerProfile(String jwt) throws SellerException {
        return null;
    }

    @Override
    public Seller createSeller(Seller seller) throws SellerException {
        return null;
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {

        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if(optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        throw new SellerException("Seller not found");
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        return null;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return null;
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {
        return null;
    }

    @Override
    public void deleteSeller(Long id) throws SellerException {

    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException {
        return null;
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {

        Seller seller = this.getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
