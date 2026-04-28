package com.example.loan.service;

import com.example.loan.dao.LoanProductRepository;
import com.example.loan.dao.entity.LoanProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LoanProductService {

    @Autowired
    private LoanProductRepository loanProductRepository;

    @CacheEvict(cacheNames = "LoanProductCache",allEntries = true)
    public int addProduct(LoanProduct loanProduct){
            if(loanProductRepository.existsByName(loanProduct.getName())){
                return 0;
            }else {
                loanProductRepository.save(loanProduct);
                return loanProduct.getId();
            }
    }

    @CacheEvict(cacheNames = "LoanProductCache",allEntries = true)
    public int updateProduct(LoanProduct loanProduct){
        if(loanProductRepository.existsByName(loanProduct.getName())){
            LoanProduct loanProduct1= loanProductRepository.getByName(loanProduct.getName());
            loanProduct.setId(loanProduct1.getId());
            loanProductRepository.save(loanProduct);
            return 1;
        }else {
            return 0;
        }
    }

    @CacheEvict(cacheNames = "LoanProductCache",allEntries = true)
    public int deleteProduct(String name){
        if(loanProductRepository.existsByName(name)){
            loanProductRepository.deleteByName(name);
            return 1;
        }else {
            return 0;
        }
    }

    @Cacheable(cacheNames = "LoanProductCache",key = "#pageable.pageNumber")
    public Page<LoanProduct> getPage(Pageable pageable){
        return loanProductRepository.findAll(pageable);
    }

    public Page<LoanProduct> getPageByKeyWord(String keyword,Pageable pageable){
        return loanProductRepository.findByKeyword(keyword,pageable);
    }
}
