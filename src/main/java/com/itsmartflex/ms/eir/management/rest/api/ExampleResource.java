package com.itsmartflex.ms.eir.management.rest.api;

import com.codahale.metrics.annotation.Timed;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.itsmartflex.ms.eir.management.service.api.dto.ExecuteRequest;
import com.itsmartflex.ms.eir.management.service.api.dto.ExecuteResponse;
import com.itsmartflex.ms.eir.management.web.api.EirManagementApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleResource implements EirManagementApiDelegate {


    @Timed
    @PreAuthorize("hasPermission({'executeRequest':#executeRequest}, 'SEARCH.MS.SEARCH.ACCESS')")
    @Override
    @PrivilegeDescription("Privilege to create porting request")
    public ResponseEntity<ExecuteResponse> executeLepFunction(ExecuteRequest executeRequest) {
        return ResponseEntity.ok().build();
    }
}
