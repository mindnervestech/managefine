package com.mnt.core.helper;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import models.FlexiAttribute;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import play.data.DynamicForm;
import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.domain.FileAttachmentMeta;
import com.mnt.core.domain.FlexiAttributes;
import com.mnt.core.ui.annotation.SearchColumn;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.core.ui.component.AddButton;
import com.mnt.core.ui.component.EditButton;
import com.mnt.core.ui.component.FieldType;
import com.mnt.core.ui.component.FieldType.Type;
import com.mnt.core.ui.component.GridActionButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.ui.component.ValueWrapper;
import com.mnt.core.ui.component.WizardStep;

public abstract class ASearchContext<M extends Model> implements SearchContext {
	protected List<FieldType> filter;
	protected Set<SearchColumn> searchColumns;
	protected Class<? extends Model> ctx;
	protected Map<String,WizardStep> wizards;
	protected Map<String,SearchContext> subSearches;
	protected String fieldNamePrefix;
	protected Model model;
	List<UIButton> buttonList = new ArrayList<UIButton>();
	protected ASearchContext(Class<? extends Model> ctx, M model){
		this.ctx = ctx;
		this.model = model;
	}
	
	private List<GridActionButton> gridActionList = new ArrayList<GridActionButton>();
	@Override
	public List<GridActionButton> getGridActions() {
		return gridActionList;
	}
	
	@Override
	public boolean isMultiSelectSearch(){
		return false;
	}
	
	@Override
	public String generateExcel() {
		return "#";
	}
	
	@Override
	public String onDoubleRowClickCallID(){
		return null;
	}
	
	public SearchContext build(){
		buildFilter();
		buildGridButton();
		buildColumns();
		buildWizard(ctx);
		buildButton();
		
		return this;
	}
	
	@Override 
	public String entityDisplayName(){
		if(entityName()!=null){
			return entityName().replace("_", " "); 
		}
		return null; 
	}
	
	protected abstract void buildButton();
	
	protected  void buildGridButton(){};
	
	@Override 
	public final List<UIButton> getButtonActions(){
		
		return buttonList;
	}
	
	@Override
	public  SearchContext withFieldNamePrefix(String fieldName){
		fieldNamePrefix = fieldName;
		return this;
	}
	
	@Override
	public UIButton showEditButton(){
		return new EditButton();
	}
	
	@Override
	public UIButton showAddButton(){
		return new AddButton();
	}
	
	
	
	protected void manageOneToOne(){
		manageOneToOne();
	}
	
	private String fieldNameWithPrefix(String fieldName){
		if(fieldNamePrefix==null){
			return fieldName;
		}else{
			return fieldNamePrefix+"."+fieldName;
		}
	}
	
	@Override
	public List<FieldType> getFilter() {
		return filter;
	}
	
	@Override
	public List<WizardStep> getWizards() {
		if(wizards==null){
			return new  ArrayList<WizardStep>();
		}
		ArrayList<WizardStep> wizardSteps = newArrayList(wizards.values());
		Collections.sort(wizardSteps);
		
		return wizardSteps;
	}
	
	@Override
	public Map<String,SearchContext> getSearchContexts() {
		return subSearches;
	}
	
	public void getModelByID(){
		
	}
	
	protected void buildFilter(){
		for(Field f :ctx.getFields()){
			if(f.isAnnotationPresent(SearchFilterOnUI.class)){
				String label = ((SearchFilterOnUI)f.getAnnotation(SearchFilterOnUI.class)).label();
				
				if(f.getType().isAssignableFrom(String.class)||f.getType().isPrimitive()){
					addFilters(Type.INPUT, label,((f.getName())), null);
				}
				if(f.getType().isAssignableFrom(Date.class)){
					addFilters(Type.DATE, label,((f.getName())), null);
				}
				
				if(f.getType().isEnum()){
					DomainEnum[] e = new DomainEnum[f.getType().getEnumConstants().length];
					for(int i = 0 ; i<e.length;i++){
						e[i] = (DomainEnum) f.getType().getEnumConstants()[i];
					}
					addFilters(Type.SELECT_OPTION, label,((f.getName())), e);
				}
			}
		}
	}
	
	protected void buildWizard(Class<? extends Model> ctx){
		for(Field f :ctx.getFields()){
			if(f.isAnnotationPresent(WizardCardUI.class)||f.isAnnotationPresent(UIFields.class)){
				WizardCardUI wizardCardUI = null;
				UIFields fieldUI = null;
				Validation validation =null;
				ValueWrapper value= new ValueWrapper();
				if(model != null){
					try {
						value.setO(f.get(model));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(f.isAnnotationPresent(Validation.class)){
					validation = ((Validation)f.getAnnotation(Validation.class));
				}
				if(f.isAnnotationPresent(WizardCardUI.class)){
					wizardCardUI = ((WizardCardUI)f.getAnnotation(WizardCardUI.class));
				}
				
				if(f.isAnnotationPresent(UIFields.class)){
					fieldUI = ((UIFields)f.getAnnotation(UIFields.class));
				}
				
				if(f.getType().isAssignableFrom(String.class)||f.getType().isPrimitive()||f.getType().isAssignableFrom(Long.class)
						||f.getType().isAssignableFrom(Float.class)||f.getType().isAssignableFrom(Double.class)
						||f.getType().isAssignableFrom(Integer.class)||
						
						f.isAnnotationPresent(OneToOne.class)){
					
					if(f.isAnnotationPresent(OneToOne.class))
					{
						try 
						{
							if(value.getO() != null){
								Class clazz = f.getType();
								value.setId(clazz.getDeclaredField("id").get(value.getO()));
							}
						}catch (Exception e)
						{
							e.printStackTrace();
						} 
					}
					if(fieldUI.uitype().equalsIgnoreCase(FieldType.Type.TEXTAREA.name())) {
						addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
								Type.TEXTAREA,null,value);
					} else {
					addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
							Type.INPUT,null,value);
					}
				}
				if(f.getType().isAssignableFrom(Date.class)){
					value.setDt((Date)value.getO());
					addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
							Type.DATE,null,value);
				}
				/*if(f.getType().isAssignableFrom(Attachment.class)){
					value.setDt((Date)value.getO());
					addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
							Type.DATE,null,value);
				}*/
				if(f.getType().isEnum()){
					if(value.getO()!=null){
						value.setDisplay(((Enum)value.getO()).name());
					}
					DomainEnum[] e = new DomainEnum[f.getType().getEnumConstants().length];
					for(int i = 0 ; i<e.length;i++){
						e[i] = (DomainEnum) f.getType().getEnumConstants()[i];
					}
					addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName())
							,Type.SELECT_OPTION,e,value);
				}
				if(f.getType().isAssignableFrom(List.class)){
                    ParameterizedType listType = (ParameterizedType)f.getGenericType();
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    if(listClass.isAssignableFrom(DomainEnum.class)){
                    	try {
                    		List<DomainEnum> list = (List<DomainEnum>) f.get(model);
                    		DomainEnum[] e = new DomainEnum[list.size()];
                    		for(int i = 0 ; i< e.length ; i++){
                    			e[i] = list.get(i);
                    		}
                    		try { //TODO: WA for USER ROle only need to make it generic
                    			doMappingforDropdown(f.getName(),value);
                    			
                    		} catch (Exception e1) { 
                        		e1.printStackTrace();
                        	}
                    		addWizard(wizardCardUI,fieldUI,validation,
                    				fieldNameWithPrefix(f.getName()),Type.SELECT_OPTION,e,value);
                    	} catch (Exception e1) { 
                    		e1.printStackTrace();
                    	}
                    }
				}
				if(f.getType().isAssignableFrom(List.class)){
                    ParameterizedType listType = (ParameterizedType)f.getGenericType();
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    if(listClass.getInterfaces().length != 0 && listClass.getInterfaces()[0].isAssignableFrom(FlexiAttributes.class)){
                    	try {
                    		List<FlexiAttribute> attributes = (List<FlexiAttribute>) FlexiAttribute.class.getMethod("getFieldsByModel", Class.class)
                					.invoke(null,listClass);
                    		
                    		if(model != null) {
                    			
                    			if(value.getO() != null) {
    								List<FlexiAttributes> flexiAttributes = (List<FlexiAttributes>) value.getO();
    								for(FlexiAttributes _attribute: flexiAttributes) {
    									boolean _found = false;
    									for(FlexiAttribute _a :attributes) {
    										if(_a.getId() == _attribute.getFlexiId()) {
    											if(_a.getType().equals("FILE")) {
    												ObjectMapper mapper = new ObjectMapper();
    												try {
    												List<FileAttachmentMeta> list = mapper.readValue(_attribute.getValue().toString(), 
    														TypeFactory.defaultInstance().constructCollectionType(List.class, FileAttachmentMeta.class));
    												_a.setValue(list);
    												//	_a.setValue(_attribute.getValue().toString());	
    												} catch(Exception e){
    													System.out.print(e.getMessage());
    												}
    											} else {
    												_a.setValue(_attribute.getValue().toString());
    												
    											}
    											_a.setModelId(_attribute.getId());
    											_found = true;
    											//attributes.add(_a);
    											break;
    										}
    									}
    									if(!_found) {
    										FlexiAttribute attribute = new FlexiAttribute();
    										attribute.setName(_attribute.getName());
    										attribute.setValue(_attribute.getValue());
    										attribute.setId(_attribute.getFlexiId());
    										attribute.setType(_attribute.getType());
    										attributes.add(attribute);
    									}
    								}
    								
    							}
                    			value.setO(attributes);
                    		} else {
                    			value.setO(attributes);
                    		}
                    		
                    		
                    		/*List<FlexiAttributes> list = (List<FlexiAttributes>) f.get(null);
                    		DomainEnum[] e = new DomainEnum[list.size()];
                    		for(int i = 0 ; i< e.length ; i++){
                    			e[i] = list.get(i);
                    		}
                    		try { //TODO: WA for USER ROle only need to make it generic
                    			//value.display = ((RoleLevel)model.getClass().getField("role").get(model)).getid + "";
                    		} catch (Exception e1) { 
                        		//e1.printStackTrace();
                        	}*/
                    		addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
    								Type.FLEXI,null,value);
                    		
                    	} catch (Exception e1) { 
                    		e1.printStackTrace();
                    	}
                    }
				}
				if(f.isAnnotationPresent(ManyToMany.class)){
					
					if(((UIFields)f.getAnnotation(UIFields.class)).searchable()){
						Class<?> listClass = null;
						try {
							if(subSearches == null){
								subSearches = new HashMap<String,SearchContext>(); 
							}
							if(f.getType().isAssignableFrom(List.class)){
								ParameterizedType listType = (ParameterizedType)f.getGenericType();
								 listClass = (Class<?>) listType.getActualTypeArguments()[0];
								 subSearches.put(fieldNameWithPrefix(f.getName()), 
										 ((SearchContext)listClass.getMethod("getSearchContext", String.class)
										 .invoke(null, f.getName())).build());
							}
							
							if(value.getO()!=null){
								for( Model m : (List<? extends Model>)value.getO()){
									if(listClass!=null){
										ValueWrapper innerValue = new ValueWrapper();
										innerValue.setO(m); 
											innerValue.setId(listClass.getField("id").get(m));
										if(value.getLi()==null){
											value.setLi(new ArrayList<ValueWrapper>());
										}
											value.getLi().add(innerValue);	
									}
								}
								
							}
							
						addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
								Type.SEARCH,null,value);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					if(((UIFields)f.getAnnotation(UIFields.class)).multiselect()){
						Class<?> listClass = null;
						try {
							if(f.getType().isAssignableFrom(List.class)){
								ParameterizedType listType = (ParameterizedType)f.getGenericType();
								 listClass = (Class<?>) listType.getActualTypeArguments()[0];
							}
							
							if(value.getO()!=null){
								for( Model m : (List<? extends Model>)value.getO()){
									if(listClass!=null){
										ValueWrapper innerValue = new ValueWrapper();
										innerValue.setO(m); 
											innerValue.setId(listClass.getField("id").get(m));
										if(value.getLi()==null){
											value.setLi(new ArrayList<ValueWrapper>());
										}
											value.getLi().add(innerValue);	
									}
								}
								
							}
							
						addWizard(wizardCardUI,fieldUI,validation,fieldNameWithPrefix(f.getName()),
								Type.INPUT,null,value);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					 
				}
			}
		}
	}
	
	protected void doMappingforDropdown(String fieldName, ValueWrapper value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
	}
	
	protected <T extends DomainEnum> void addWizard(final WizardCardUI wizardCardUI, final UIFields field,final Validation validation ,final String nameForMaping,final FieldType.Type type,
			final T[] options,final ValueWrapper value) {
		this.addWizard(wizardCardUI, field, validation, nameForMaping, type, options, value,null);
	}
	protected <T extends DomainEnum> void addWizard(final WizardCardUI wizardCardUI, final UIFields field,final Validation validation ,final String nameForMaping,final FieldType.Type type,
			final T[] options,final ValueWrapper value, final List<FlexiAttribute> attributes) {
		if(wizards == null){
			wizards = new HashMap<String,WizardStep>();
		}
		
		final FieldType card = new FieldType() {
			
			@Override
			public int compareTo(FieldType o) {
				return (Integer.valueOf(order()).compareTo(o.order()));
			}
			
			
			@Override
			public int order() {
				return field.order();
			}
			
			@Override
			public String label() {
				return field.label();
			}

			@Override
			public Type ctype() {
				return type;
			}
			
			@Override
			public boolean hidden(){
				return field.hidden();
			}

			@Override
			public String name() {
				return nameForMaping;
			}
			
			@Override
			public boolean autocomplete(){
				return field.autocomplete();
			}
			
			@Override
			public boolean multiselect(){
				return field.multiselect();
			}

			@Override
			public DomainEnum[] options() {
				return (options);
			}
			
			@Override
			public String toString() {
				return label();
			}

			@Override
			public boolean isSearchable() {
				return field.searchable();
			}

			@Override
			public ValueWrapper value() {
				return value;
			}


			@Override
			public boolean mandatory() {
				return field.mandatory();
			}


			@Override
			public JQValidation validation() {
				return validation==null?null:new JQValidation() {
					
					@Override
					public boolean required() {
						return validation.required();
					}
					
					@Override
					public int minlength() {
						return validation.minlength();
					}
					
					@Override
					public String messages() {
						return validation.messages();
					}
					
					@Override
					public int maxlength() {
						return validation.maxlength();
					}
					
					@Override
					public boolean email() {
						return validation.email();
					}
					
					@Override
					public boolean date() {
						return validation.date();
					}

					@Override
					public boolean number() {
						return validation.number();
					}

					@Override
					public boolean digits() {
						return validation.digits();
					}

					@Override
					public String remote() {
						return validation.remote();
					}
				};
			}


			@Override
			public int row() {
				// TODO Auto-generated method stub
				return field.row();
			}


			@Override
			public int col() {
				return field.col();
			}


			@Override
			public String cssClass() {
				return field.cssClass();
			}


			@Override
			public String htmlAttrib() {
				return field.htmlAttrib();
			}


			@Override
			public String ajaxDependantField() {
				return field.ajaxDependantField();
				
			}

			
		};
		
		
		
		if(wizards.containsKey(wizardCardUI.name()))
		{
			wizards.get(wizardCardUI.name()).card().add(card);
		}
		else
		{
			WizardStep wizardStep = new WizardStep() {
				
				SortedSet<FieldType> fields;
				@Override
				public int step() {
					return wizardCardUI.step();
				}
				
				@Override
				public String name() {
					return wizardCardUI.name();
				}
				
				@Override
				public SortedSet<FieldType> card() {
					if(fields==null){
						fields = new TreeSet<FieldType>();
					}else{
						//fields.add(card);
					}
					return fields;
				}

				@Override
				public int compareTo(WizardStep o) {
					return (Integer.valueOf(step()).compareTo(o.step()));
				}
				
				@Override
				public boolean equals(Object arg0) {
					return name().equals(arg0.toString());
				}
				
				@Override
				public int hashCode() {
					return name().hashCode();
				}
				
				@Override
				public String toString() {
					return name();
				}
			};
			wizardStep.card().add(card);
			wizards.put(wizardCardUI.name(), wizardStep);
		}
		
	}
	
	
	protected void buildColumns(){
		for(Field f :ctx.getFields()){
			if(f.isAnnotationPresent(SearchColumnOnUI.class)){
				String label = ((SearchColumnOnUI)f.getAnnotation(SearchColumnOnUI.class)).colName();
				int rank = ((SearchColumnOnUI)f.getAnnotation(SearchColumnOnUI.class)).rank();
				int width = ((SearchColumnOnUI)f.getAnnotation(SearchColumnOnUI.class)).width();
				
				if(f.getType().isAssignableFrom(String.class)||f.getType().isPrimitive()||f.getType().isEnum()
						||f.getType().isAssignableFrom(Long.class)
						||f.getType().isAssignableFrom(Float.class)||f.getType().isAssignableFrom(Double.class)
						||f.getType().isAssignableFrom(Integer.class)||
						f.getType().isAssignableFrom(Date.class)||
						f.isAnnotationPresent(OneToOne.class)){
					addColumns(rank, label,(f.getName()),width);
				}
				
			}
		}
	}
	
	protected Expression doSearchExpression(DynamicForm form,SearchType searchType){
		List<Expression> expressions = new ArrayList<Expression>();
		
		for(Field f :ctx.getFields()){
			if(f.isAnnotationPresent(SearchFilterOnUI.class) && !f.isAnnotationPresent(Transient.class)){
				String fieldVal = form.get(fieldNameWithPrefix(f.getName()));
				if(f.getType().isEnum()){
					if("All".equals(fieldVal)){
						continue;
					}
				}
				if(fieldVal!=null && fieldVal.length()>0){
					expressions.add( Expr.ilike(fieldNameWithPrefix(f.getName()), "%"+fieldVal+"%"));
				}
			}
		}
		
		Expression exp=null;
		if(expressions.size()!=0)
		{
			exp = expressions.get(0);
			for(int i =1;i<expressions.size();i++)
			{
				exp = Expr.and(exp, expressions.get(i));
			}
		}
		
		return exp;
		
	}
	
	protected <T extends Model> List<String> getResultStr(T o) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		List<SearchColumn> list = new ArrayList<SearchColumn>();
		list.addAll(searchColumns);
		List<String> resultStr = newArrayList();
		System.out.println(o);
			for(SearchColumn col :list){
				if(ctx.getField(col.name()).get(o) != null){
					if(ctx.getField(col.name()).get(o) instanceof DomainEnum){
						resultStr.add(((DomainEnum)ctx.getField(col.name()).get(o)).getName());
					}else{
						resultStr.add(ctx.getField(col.name()).get(o).toString());
					}
					
				}else{
					resultStr.add("");
				}
			}

			resultStr.add(ctx.getField("id").get(o).toString());
		return 	resultStr;
		
	}
	
	protected <T extends Model> List<String> getResultStr(Class ctx, T o) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		List<SearchColumn> list = new ArrayList<SearchColumn>();
		list.addAll(searchColumns);
		List<String> resultStr = newArrayList();
		System.out.println(o);
			for(SearchColumn col :list){
				if(ctx.getField(col.name()).get(o) != null){
					if(ctx.getField(col.name()).get(o) instanceof DomainEnum){
						resultStr.add(((DomainEnum)ctx.getField(col.name()).get(o)).getName());
					}else{
						resultStr.add(ctx.getField(col.name()).get(o).toString());
					}
					
				}else{
					resultStr.add("");
				}
			}

			resultStr.add(ctx.getField("id").get(o).toString());
		return 	resultStr;
		
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form){
		return null;
	}
	
	public <T extends Model> HSSFWorkbook getExcelExport(List<T> os) throws Exception{
		
		List<SearchColumn> list = new ArrayList<SearchColumn>();
		list.addAll(searchColumns);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfWorkbook.createSheet("Report");
		HSSFRow header = sheet.createRow(0);
		HSSFFont font = hssfWorkbook.createFont();
        font.setBoldweight((short) 700);
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setFont(font);
        for(SearchColumn col :list){
	        HSSFCell cell = header.createCell(col.rank());
	        cell.setCellStyle(headerStyle);
	        cell.setCellValue(col.colName());
        }
        int rowNum = 1;
        for(T o: os){
        	HSSFRow _row = sheet.createRow(rowNum++);
            for(SearchColumn col :list){
    			if(ctx.getField(col.name()).get(o) != null){
    				_row.createCell(col.rank()).setCellValue(ctx.getField(col.name()).get(o).toString());
    			}else{
    				_row.createCell(col.rank()).setCellValue("");
    			}
    			
    		}
        }
        
        for(SearchColumn col :list){
        	sheet.autoSizeColumn(col.rank());
        }
        
        
        return hssfWorkbook;
		
	}
	
	@Override
	public List<SearchColumn> getColumn() {
		List<SearchColumn> list = new ArrayList<SearchColumn>();
		list.addAll(searchColumns);
		return list;
	};
	
	protected void addColumns(final int rank,final String colName,final String name, final int width){
		if(searchColumns == null){
			searchColumns = new TreeSet<SearchColumn>();
		 }
		
		searchColumns.add(new SearchColumn() {
			
			@Override
			public int rank() {
				return rank;
			}
			
			@Override
			public String colName() {
				return colName;
			}
			
			@Override
			public int width() {
				return width;
			}

			@Override
			public String name() {
				return name;
			}

			@Override
			public int compareTo(SearchColumn o) {
				return Integer.valueOf(rank()).compareTo(o.rank());
			}
			
			public String toString(){
				return colName;
			}
		});
	}
	
	protected <T extends DomainEnum> void  addFilters(final Type type,final String label,final String name,
			final T[] options){
		 
		if(filter == null){
			 filter = new ArrayList<FieldType>();
		 }
		 
			 filter.add(new FieldType() {
				
				@Override
				public Type ctype() {
					return type;
				}
				
				@Override
				public boolean isSearchable() {
					return false;
				}
				
				@Override
				public DomainEnum[] options() {
					//return EnumUtils.enumNameToStringArray(options);
					return options;
				}
				
				@Override
				public String label() {
					return label;
				}

				@Override
				public String name() {
					return name;
				}

				@Override
				public int compareTo(FieldType o) {
					return 0;
				}
				
				@Override
				public boolean hidden() {
					return false;
				}

				@Override
				public int order() {
					return 0;
				}

				@Override
				public boolean autocomplete() {
					return false;
				}

				@Override
				public ValueWrapper  value() {
					return null;
				}

				@Override
				public boolean mandatory() {
					return false;
				}

				@Override
				public JQValidation validation() {
					return null;
				}

				@Override
				public int row() {
					return 0;
				}

				@Override
				public int col() {
					return 0;
				}

				@Override
				public String cssClass() {
					return null;
				}

				@Override
				public String htmlAttrib() {
					return null;
				}
				@Override
				public boolean multiselect(){
					return false;
				}

				@Override
				public String ajaxDependantField() {
					// TODO Auto-generated method stub
					return null;
				}
			});
		 
		
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return null;
	}

	public Set<SearchColumn> getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(Set<SearchColumn> searchColumns) {
		this.searchColumns = searchColumns;
	}

	public Class<? extends Model> getCtx() {
		return ctx;
	}

	public void setCtx(Class<? extends Model> ctx) {
		this.ctx = ctx;
	}

	public Map<String, SearchContext> getSubSearches() {
		return subSearches;
	}

	public void setSubSearches(Map<String, SearchContext> subSearches) {
		this.subSearches = subSearches;
	}

	public String getFieldNamePrefix() {
		return fieldNamePrefix;
	}

	public void setFieldNamePrefix(String fieldNamePrefix) {
		this.fieldNamePrefix = fieldNamePrefix;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<GridActionButton> getGridActionList() {
		return gridActionList;
	}

	public void setGridActionList(List<GridActionButton> gridActionList) {
		this.gridActionList = gridActionList;
	}

	public void setFilter(List<FieldType> filter) {
		this.filter = filter;
	}

	public void setWizards(Map<String, WizardStep> wizards) {
		this.wizards = wizards;
	}

	public List<UIButton> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<UIButton> buttonList) {
		this.buttonList = buttonList;
	}

}
