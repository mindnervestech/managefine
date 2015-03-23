package com.mnt.core.utils;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GridViewModel {

	public int page;
    public int total;
    public int records;
    public List<RowViewModel> rows;

    public int getPage() {
 		return page;
 	}

 	public void setPage(int page) {
 		this.page = page;
 	}

 	public int getTotal() {
 		return total;
 	}

 	public void setTotal(int total) {
 		this.total = total;
 	}

 	public int getRecords() {
 		return records;
 	}

 	public void setRecords(int records) {
 		this.records = records;
 	}

 	public List<RowViewModel> getRows() {
 		return rows;
 	}

 	public void setRows(List<RowViewModel> rows) {
 		this.rows = rows;
 	}

    public GridViewModel(int page, int total, int records, List<RowViewModel> rows) {
        this.page = page;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }

    public GridViewModel(Paging pageData, int totalEntries, List<RowViewModel> rows) {
        this(pageData.pageIndex(), totalPagesFrom(totalEntries,pageData.rowsPerPage()), totalEntries, rows);
    }

    public static int totalPagesFrom(int totalEntries, int rowsPerPage) {
        double pages = (double)totalEntries/(double)rowsPerPage;
        if(pages == 0d) {
            return 1;
        }
        return (int) Math.ceil(pages);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class RowViewModel {
        public int id;
        public List<String> cell;

        public RowViewModel(int id, List<String> cell) {
            this.id = id;
            this.cell = cell;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public List<String> getCell() {
			return cell;
		}

		public void setCell(List<String> cell) {
			this.cell = cell;
		}
    }
    
   

    public static class PageData implements Paging {
        // The names of the fields are in line with the URL parameters sent by jQGrid.
        private int rows;
        private int page;

        public PageData() { // required for the Spring MVC data binding
        }

        public PageData(int rows, int page) {
            this.rows = rows;
            this.page = page;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public void setPage(int page) {
            this.page = page;
        }

        //@Override
        public int rowsPerPage() {
            return rows;
        }

        //@Override
        public int pageIndex() {
            return page;
        }
    }
    


    public static class SortData implements Sorting {
        // The names of the fields are in line with the URL parameters sent by jQGrid.
        private String sidx;
        private String sord;

        public void setSidx(String sidx) {
            this.sidx = sidx;
        }

        public void setSord(String sord) {
            this.sord = sord;
        }

        //@Override
        public String sortBy() {
            return sidx;
        }

        //@Override
        public SortOrder sortOrder() {
            return "desc".equals(sord) ? SortOrder.DESCENDING: SortOrder.ASCENDING;
        }

		public String getSidx() {
			return sidx;
		}

		public String getSord() {
			return sord;
		}
    }
}
