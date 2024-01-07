package pl.janksiegowy.backend.subdomain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.List;

public class SubdomainRequestCondition implements RequestCondition<SubdomainRequestCondition>{

    private final List<String> subdomain;

    public SubdomainRequestCondition( List<String> subdomain){
        System.err.println( "SubdomainRequestCondition: "+ subdomain);
        this.subdomain= subdomain;
    }

    @Override
    public SubdomainRequestCondition combine(SubdomainRequestCondition other) {
        return null;
    }

    @Override
    public SubdomainRequestCondition getMatchingCondition( HttpServletRequest request) {
        if( subdomain.contains( request.getServerName().replaceAll("\\..*", ""))) {
            return this;
        };
        return null;
    }

    @Override
    public int compareTo(SubdomainRequestCondition other, HttpServletRequest request) {
        return 0;
    }

}
