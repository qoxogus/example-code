package proxy_pattern;

public class Service implements IService {

    @Override
    public String runSomething() {
        return "서비스입니다.";
    }
}
