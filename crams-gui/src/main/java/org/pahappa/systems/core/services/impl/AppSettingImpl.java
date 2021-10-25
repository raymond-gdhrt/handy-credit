package org.pahappa.systems.core.services.impl;

import javax.ws.rs.core.MediaType;

import org.pahappa.systems.core.dao.AppSettingDao;
import org.pahappa.systems.core.services.AppSettingService;
import org.pahappa.systems.models.AppSetting;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Service
@Transactional
public class AppSettingImpl implements AppSettingService {

    @Autowired
    private AppSettingDao appSettingDao;

    @Autowired
    CountryDao countryDao;

    @Override
    public AppSetting save(AppSetting appSetting) throws ValidationFailedException {

        if (appSetting.getEgosmsUrl() == null) {
            throw new ValidationFailedException("Missing Egosms URL");
        }

        if (appSetting.getEgoSmsApiUsername() == null) {
            throw new ValidationFailedException("Missing Egosms username");
        }

        if (appSetting.getEgoSmsApiPassword() == null) {
            throw new ValidationFailedException("Missing Egosms password");
        }

        if (appSetting.getPaymentUrl() == null) {
            throw new ValidationFailedException("Missing Payments URL");
        }

        if (appSetting.getPaymentApiUsername() == null) {
            throw new ValidationFailedException("Missing Payments username");
        }

        if (appSetting.getPaymentApiPassword() == null) {
            throw new ValidationFailedException("Missing Payments password");
        }

       

        return appSettingDao.save(appSetting);
    }

    @Override
    public Country getCountryByName(String countryName) {
        return countryDao.searchUniqueByPropertyEqual("name", countryName);
    }

    @Override
    public AppSetting getAppSetting() {
        if (appSettingDao.findAll().size() > 0) {
            return appSettingDao.findAll().get(0);
        }
        return null;
    }

    public int testUrls(String url) {
        WebResource resource = Client.create(new DefaultClientConfig())
                .resource(url);
        final Builder webResource = resource.accept(MediaType.APPLICATION_JSON);
        webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse clientResponse = webResource.post(ClientResponse.class, String.class.toString());
        System.out.println("Status " + clientResponse.getStatus());
        System.out.println("Response " + clientResponse.toString());
        return clientResponse.getStatus();
    }

}
