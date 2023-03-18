package it.sebastianosuraci.springboot.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// http://localhost:4200/api/installation?filterDict[name]=aaa&pageStart=1&pageItems=20&orderField=name&orderDir=asc

@Getter
@Setter
@NoArgsConstructor
public class PageModel {

	protected Map<String, String> f = new TreeMap<>();
	protected String fetchProfile;
	protected Integer pageStart = 1;
	protected Integer pageItems = 20;
	protected Long fetchedRows;
	protected List<String> sort = new ArrayList<>();

}
