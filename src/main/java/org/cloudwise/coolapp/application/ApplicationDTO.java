package org.cloudwise.coolapp.application;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cloudwise.coolapp.common.Level;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private String id;
    private String name;
    private String url;
    private Level level;
}
