package cz.profinit.training.springadvanced.proxies;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProxiedServiceImpl implements ProxiedService {

    @Override
    @Transactional
    public String a() {
        return "a" + b();
    }

    @Override
    @Transactional
    public String b() {
        return "b";
    }
}
