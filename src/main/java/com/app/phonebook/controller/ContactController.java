package com.app.phonebook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.phonebook.constants.ApplicationConstant;
import com.app.phonebook.entity.Contact;
import com.app.phonebook.props.ApplicationProperties;
import com.app.phonebook.service.ContactService;

@Controller
public class ContactController {

	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired(required = true)
	private ContactService contactService;

	@Autowired
	private ApplicationProperties appProperties;

	@GetMapping("/loadForm")
	public String loadForm(Model model) {

		logger.debug("loadForm() method execution is started************************");

		model.addAttribute("contact", new Contact());
		logger.debug("loadForm() method execution is ended************************");

		logger.info("Contact form is loaded successfully *************************");

		return "index";
	}

	@PostMapping("/saveContact")
	public String handleSaveContactBtn(Contact contact, RedirectAttributes redirect) {

		logger.debug("handleSaveContact() execution is started *****************************");

		Map<String, String> messages = appProperties.getMessages();

		String msgText = null;
		if (contact.getContactId() == null) {
			msgText = messages.get(ApplicationConstant.SUCCESS_MSG);

		} else {
			msgText = messages.get(ApplicationConstant.UPDATE_MSG);
		}
		contact.setActiveSw("Y");
		boolean saveContact = contactService.saveContact(contact);
		if (saveContact) {

			logger.info("Contact Save Successfully ********************************");
			redirect.addFlashAttribute("succMsg", msgText);

		} else {
			logger.info("Contact is Fail ******************************************");
			redirect.addFlashAttribute("errMsg", messages.get(ApplicationConstant.FAILED_TO_SAVE));
		}

		logger.debug("handleSaveContact() execution is ended *****************************");

		logger.info("Contact Stored Successfully *****************************************");

		return "redirect:/loadForm";
	}

	@GetMapping("/viewContacts")
	public String handleViewContactBtn(Model model) {

		logger.debug(" handleViewContact() method execution is started ************************");

		List<Contact> allContacts = new ArrayList<>();

		if (allContacts.isEmpty()) {
			logger.info("Contacts is Empty ************************************************");
		}

		allContacts = contactService.getAllContacts();
		model.addAttribute("contacts", allContacts);

		logger.debug(" handleViewContact() method execution is ended ************************");

		logger.info("Contacts is Displayed Successfully *************** " + allContacts);
		return "viewContacts";
	}

	@GetMapping("/edit")
	public String handleEditClick(@RequestParam("cid") Integer cid, Model model) {

		logger.debug("handleEditClick() method execution is started ********************************");

		Contact conatct = contactService.getContactById(cid);
		model.addAttribute("contact", conatct);
		logger.debug("handleEditClick() method execution is ended ********************************");

		logger.info("Contact is updated Successfully *******************************************");
		return "index";
	}

	@GetMapping("/delete")
	public String handleDeleteClick(@RequestParam("cid") Integer cid, Model model) {

		logger.debug("handleDeleteClick() method execution is started ******************************");

		contactService.deleteContactById(cid);

		logger.debug("handleDeleteClick() method execution is ended ******************************");

		logger.info("Contact is Deleted Successfully *****************************************");
		return "redirect:/viewContacts";
	}

}
