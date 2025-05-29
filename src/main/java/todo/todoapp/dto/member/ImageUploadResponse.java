package todo.todoapp.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Schema(description = "이미지 업로드 응답")
public class ImageUploadResponse {
    @Schema(description = "이미지 URL", example = "https://myapp.com/images/abc123.png")
    private String imageUrl;
}
