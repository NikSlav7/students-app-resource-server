package com.example.ResourceServer.pdf;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.pdfbox.text.TextPosition;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LastMark {

    StringBuilder string;
    List<TextPosition> textPositionList;


    public void mergeList(List<TextPosition> positions){
        for (TextPosition position : positions) textPositionList.add(position);
    }
}
