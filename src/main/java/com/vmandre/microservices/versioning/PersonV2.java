package com.vmandre.microservices.versioning;

import com.vmandre.microservices.versioning.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonV2 {
    private Name name;
}
