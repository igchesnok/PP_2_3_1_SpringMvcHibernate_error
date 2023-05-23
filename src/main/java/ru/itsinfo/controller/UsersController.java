package ru.itsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itsinfo.model.User;
import ru.itsinfo.service.UserService;

@Controller
public class UsersController {

	private final UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping (value = "/") //связываем url страници с методом контроллера
	public String showUsers(Model model){
		//добавляем в модель атрибут list содержащий всех работников List<User>
		model.addAttribute("list", userService.getAllUsers( ));
		//определяем возвращаемую страницу
		return "users_list";
	}

	@GetMapping (value = "/addNewUser") //связываем url страници с методом контроллера
	public String showAddUsers(Model model){
		//добавляем в модель атрибут user содержащий объект User
		model.addAttribute("user", new User());
		//определяем возвращаемую страницу
		return "users_add";
	}

	@GetMapping (value = "/{id}/editUser") //связываем url страници с методом контроллера
	public String showEditUsers(Model model, RedirectAttributes attributes,
								@PathVariable(value = "id", required = true) long id){
		User user = userService.readUser(id);
		if (null == user) {
			attributes.addFlashAttribute("flashMessage", "Пользователь не существует!");
			return "redirect:/users_list";
		}
		//добавляем в модель атрибут user содержащий объект User
		model.addAttribute("user",user);
		//определяем возвращаемую страницу
		return "users_add";
	}

	@PostMapping  (path ="/saveUser") //связываем url страници с методом контроллера
	public String saveUsers(@ModelAttribute("user") User user){
		//добавляем в модель атрибут user содержащий объект User
		userService.createOrUpdateUser(user);
		//определяем возвращаемую страницу
		return "redirect:/";
	}

	@GetMapping   ("/deleteUser") //связываем url страници с методом контроллера
	public String deleteUser(@RequestParam(value = "id") long id){
		//добавляем в модель атрибут user содержащий объект User
		User user = userService.deleteUser(id);
		//определяем возвращаемую страницу
		return "redirect:/";
	}
}