package com.prisma.prismabooking.service;

import com.google.gson.Gson;
import com.prisma.prismabooking.component.ConfigurationComponent;
import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;

@org.springframework.stereotype.Service
public class ServiceService extends BaseService<Service> {

    public static Resource serviceFile;
    private StructureService structureService;

    public ServiceService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        super.gson = gson;
        super.getterOfPrimaryKey = "getName";
    }

    @Autowired
    public void setStructureService(StructureService structureService){
        this.structureService = structureService;
    }

    @PostConstruct
    private void init() {
        serviceFile = init(Service.class, "/services.json");
    }

    public PagedResponse<Service> findServicePage(Integer offset, Integer limit) {
        return findPage(list, offset, limit);
    }

    public Service findService(String serviceName) {
        return getSingle(serviceName);
    }

    public Service createService(Service service) {
        return createNew(service, serviceFile);
    }

    public Service updateService(String serviceName, Service service) {
        if(!service.getName().equalsIgnoreCase(serviceName)){
            structureService.list.forEach(s->{
                s.getServices().remove(serviceName);
                s.getServices().add(service.getName());
            });
            structureService.updateJson(StructureService.structureFile);
        }
        return updateSingle(service, serviceName, serviceFile);
    }

    public void deleteFromFile(String serviceName) {
        structureService.list.forEach(s->s.getServices().remove(serviceName));
        structureService.updateJson(StructureService.structureFile);
        deleteSingle(serviceName, serviceFile);
    }
}
