package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);

    SellerReport updateSellerReport( SellerReport sellerReport);
}
