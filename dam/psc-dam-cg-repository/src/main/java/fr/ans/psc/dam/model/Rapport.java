package fr.ans.psc.dam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Rapport de chargement du fichier dans mongoDB.
 */
@ApiModel(description = "Rapport de chargement du fichier dans mongoDB.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-17T15:39:39.535933500+01:00[Europe/Paris]")
public class Rapport   {
  @JsonProperty("valid")
  private Boolean valid;

  @JsonProperty("numberPS")
  private String numberPS;

  public Rapport valid(Boolean valid) {
    this.valid = valid;
    return this;
  }

  /**
   * Get valid
   * @return valid
  */
  @ApiModelProperty(value = "")


  public Boolean getValid() {
    return valid;
  }

  public void setValid(Boolean valid) {
    this.valid = valid;
  }

  public Rapport numberPS(String numberPS) {
    this.numberPS = numberPS;
    return this;
  }

  /**
   * Get numberPS
   * @return numberPS
  */
  @ApiModelProperty(value = "")


  public String getNumberPS() {
    return numberPS;
  }

  public void setNumberPS(String numberPS) {
    this.numberPS = numberPS;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rapport rapport = (Rapport) o;
    return Objects.equals(this.valid, rapport.valid) &&
        Objects.equals(this.numberPS, rapport.numberPS);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valid, numberPS);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rapport {\n");
    
    sb.append("    valid: ").append(toIndentedString(valid)).append("\n");
    sb.append("    numberPS: ").append(toIndentedString(numberPS)).append("\n");
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

