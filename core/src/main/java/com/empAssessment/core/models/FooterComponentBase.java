package com.empAssessment.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
public class FooterComponentBase {
    @ValueMapValue
    private String fileReference;
    @ValueMapValue
    private String description;
    @ValueMapValue
    private String title;

    @ChildResource
    private List<FooterComponentModel> actions = new ArrayList<>();

}
