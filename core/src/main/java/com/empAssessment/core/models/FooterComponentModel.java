package com.empAssessment.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
public class FooterComponentModel {
    @ValueMapValue
    private String descriptionButton;
    @ValueMapValue
    private String linkTarget;


}
