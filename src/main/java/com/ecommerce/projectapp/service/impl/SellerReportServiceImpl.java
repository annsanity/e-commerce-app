package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.SellerReport;
import com.ecommerce.projectapp.repository.SellerReportRepository;
import com.ecommerce.projectapp.service.SellerReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

    private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {

        SellerReport report = sellerReportRepository.findBySeller_Id(seller.getId());
        if(report == null){
            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);
            return sellerReportRepository.save(newReport);
        }

        return report;
    }


    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {

        return sellerReportRepository.save(sellerReport);
    }
}
