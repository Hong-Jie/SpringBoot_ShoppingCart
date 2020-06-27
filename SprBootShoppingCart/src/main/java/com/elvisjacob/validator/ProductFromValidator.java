package com.elvisjacob.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.elvisjacob.dao.ProductDAO;
import com.elvisjacob.entities.Product;
import com.elvisjacob.form.ProductForm;

@Component
public class ProductFromValidator implements Validator {

	@Autowired
	private ProductDAO productDao;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == ProductForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductForm productForm = (ProductForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "Nonempty.productFrom.code");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Nonempty.productFrom.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Nonempty.productFrom.price");
		
		String code = productForm.getCode();
		if (code != null && code.length() > 0) {
			if (code.matches("\\P+")) {
				errors.rejectValue("code", "Pattern.productForm.code");
			} else if (productForm.isNewProduct()) {
				Product product = productDao.findProduct(code);
	            if (product != null) {
	            	errors.rejectValue("code", "Duplicate.productForm.code");
	            }
			}
		}
	}

}
