package fr.ans.psc.dam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.ans.psc.dam.model.RichDam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PsDAMs
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-18T16:11:05.115531+01:00[Europe/Paris]")
public class PsDAMs   {
  @JsonProperty("nationalId")
  private String nationalId;

  @JsonProperty("dams")
  @Valid
  private List<RichDam> dams = null;

  public PsDAMs nationalId(String nationalId) {
    this.nationalId = nationalId;
    return this;
  }

  /**
   * Get nationalId
   * @return nationalId
  */
  @ApiModelProperty(value = "")


  public String getNationalId() {
    return nationalId;
  }

  public void setNationalId(String nationalId) {
    this.nationalId = nationalId;
  }

  public PsDAMs dams(List<RichDam> dams) {
    this.dams = dams;
    return this;
  }

  public PsDAMs addDamsItem(RichDam damsItem) {
    if (this.dams == null) {
      this.dams = new ArrayList<RichDam>();
    }
    this.dams.add(damsItem);
    return this;
  }

  /**
   * Get dams
   * @return dams
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<RichDam> getDams() {
    return dams;
  }

  public void setDams(List<RichDam> dams) {
    this.dams = dams;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PsDAMs psDAMs = (PsDAMs) o;
    return Objects.equals(this.nationalId, psDAMs.nationalId) &&
        Objects.equals(this.dams, psDAMs.dams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nationalId, dams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PsDAMs {\n");
    
    sb.append("    nationalId: ").append(toIndentedString(nationalId)).append("\n");
    sb.append("    dams: ").append(toIndentedString(dams)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

