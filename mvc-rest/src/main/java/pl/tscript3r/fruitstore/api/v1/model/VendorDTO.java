package pl.tscript3r.fruitstore.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @ApiModelProperty(notes = "Company name of the vendor", required = true)
    private String name;

    @ApiModelProperty(notes = "Vendors home page URL", required = true)
    @JsonProperty("vendor_url")
    private String vendorUrl;

}
