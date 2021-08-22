package it.sebastianosuraci.springboot.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

// http://localhost:4200/api/installation?filterDict[name]=aaa&pageStart=1&pageItems=20&orderField=name&orderDir=asc

@Data
public class PageModel {

	protected Map<String, String> f = new TreeMap<>();
	protected String fetchProfile;
	protected Integer pageStart = 1;
	protected Integer pageItems = 20;
	protected Integer fetchedRows;
	protected List<String> sort = new ArrayList<>();

}
