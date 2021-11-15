package com.github.jgzl.infra.upms.domain.vo;
import com.github.jgzl.infra.upms.domain.enums.ReceiverType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Levin
 */
@Data
public class StationMessagePublishResp {
    private Long id;
    private String title;
    private String level;
    private ReceiverType type;
    private List<Long> receiver;
    private String description;
    private String content;
    private String createdName;
    private LocalDateTime createdTime;
}
