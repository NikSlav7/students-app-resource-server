package com.example.ResourceServer.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadDeleteDTO {
    @NonNull
    private String uploadId;
}
