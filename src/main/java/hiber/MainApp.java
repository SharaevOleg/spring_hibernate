package hiber;

import hiber.config.AppConfig;
import hiber.model.User;
import hiber.model.Car;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Car car = new Car("Ford Mustang");
        Car car1 = new Car("BMW X5");
        Car car2 = new Car("Mitsubishi TOPO");

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("Вася", "Петров", "vasya@mail.ru", car));
        userService.add(new User("Петя", "Иванов", "petya@mail.ru", car1));
        userService.add(new User("Коля", "Сюваев", "kolya@mail.ru", car2));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car model = " + user.getCar().getModel());
            System.out.println("________________________________________");
        }

        User user = userService.getByCarModelAndSeries("Ford Mustang", 1);
        System.out.println(user.getFirstName() +" "+ user.getLastName());

        context.close();

    }
}
