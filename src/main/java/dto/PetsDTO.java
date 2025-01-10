package dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class PetsDTO {
    private Long id;
    private CategoryDTO category;
    private String name;
    private List<String> photoUrls;
    private List<TagsDTO> tags;
    private String status;
}