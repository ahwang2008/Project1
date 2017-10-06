/**
 * Created by Hwang on 5/30/2017.
 */

public class TestDriver {

    public static void main(String[] args){
        org.junit.runner.JUnitCore.runClasses(
                dataAccess.AuthTokenDaoTest.class,
                dataAccess.DatabaseTest.class,
                dataAccess.EventDaoTest.class,
                dataAccess.PersonDaoTest.class,
                dataAccess.UserDaoTest.class,
                model.AuthTokenTest.class,
                model.EventTest.class,
                model.PersonTest.class,
                model.UserTest.class,
                service.ClearServiceTest.class,
                service.EventIDServiceTest.class,
                service.EventServiceTest.class,
                service.FillServiceTest.class,
                service.LoadServiceTest.class,
                service.LoginServiceTest.class,
                service.PersonIDServiceTest.class,
                service.PersonServiceTest.class,
                service.RegisterServiceTest.class,
                service.ServiceTest.class
        );

        org.junit.runner.JUnitCore.main(
                "dataAccess.AuthTokenDaoTest",
                "dataAccess.DatabaseTest",
                "dataAccess.EventDaoTest",
                "dataAccess.PersonDaoTest",
                "dataAccess.UserDaoTest",
                "model.AuthTokenTest",
                "model.EventTest",
                "model.PersonTest",
                "model.UserTest",
                "service.ClearServiceTest",
                "service.EventIDServiceTest",
                "service.EventServiceTest",
                "service.FillServiceTest",
                "service.LoadServiceTest",
                "service.LoginServiceTest",
                "service.PersonIDServiceTest",
                "service.PersonServiceTest",
                "service.RegisterServiceTest",
                "service.ServiceTest"
        );
    }
}
