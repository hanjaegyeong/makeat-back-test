package io.makeat.makeat_be.dto;

import io.makeat.makeat_be.entity.DietRecord;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
public class SimpleRecordDto {
    private long recordId;
    private String imgUrl;
    private String date;
    private String time;

    public SimpleRecordDto(DietRecord dietRecord) {
        this.recordId = dietRecord.getRecordId();
        this.imgUrl = dietRecord.getImgUrl();
        this.date = dietRecord.getDate();
        this.time = dietRecord.getTime();
    }

}
