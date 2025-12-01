import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.UserServiceImpl;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.UserService;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import org.junit.jupiter.api.Test;

public class UserTest {


    @Test
    public void insetUser() {
        UserService userService = UserServiceImpl.getInstance();
        UserEntity test = new UserEntity(1, "test@test.com", "test", "test", true);
        Object res = userService.register(test);
        AppUtils.print(res.toString());
    }

    @Test
    public void loginTest() {
        UserService userService = UserServiceImpl.getInstance();
        Object res = userService.login("test@test.com", "test");
        AppUtils.print(res.toString());
    }


}
