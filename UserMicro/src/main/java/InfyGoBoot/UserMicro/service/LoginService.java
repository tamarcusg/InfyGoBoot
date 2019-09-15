/**
 * 
 */
package InfyGoBoot.UserMicro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import InfyGoBoot.UserMicro.dto.LoginDetails;
import InfyGoBoot.UserMicro.entity.Customer;
import InfyGoBoot.UserMicro.exception.ExceptionConstants;
import InfyGoBoot.UserMicro.exception.InfyGoServiceException;
import InfyGoBoot.UserMicro.repository.CustomerRepository;

/**
 * The Class AadharService.
 */
@Service
public class LoginService {

	@Autowired
	private CustomerRepository customerRepository;

	public boolean isUserValid(LoginDetails customerLogin) throws InfyGoServiceException {

		Customer customer = customerRepository.findById(customerLogin.getUserId()).get();

		if (customer == null) {
			throw new InfyGoServiceException(ExceptionConstants.USER_NOT_FOUND.toString(), "User record not found");
		} else if (!(customer.getPassword().equals(customerLogin.getPassword()))) {
			throw new InfyGoServiceException(ExceptionConstants.USER_INVALID.toString(), "Invalid credentials");
		}
		return true;

	}

}
