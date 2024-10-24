package uom.ics22116.atmservice.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontendUser {
    
  private Integer id;

  private String firstname;

  private String lastname;

  private String email;
}
