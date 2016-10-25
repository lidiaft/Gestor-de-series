package abd.pr1.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import abd.pr1.tiposDeDatos.Episodio;

@SuppressWarnings("serial")
public class EpisodioTable extends AbstractTableModel {

		String[] columnNames = { "Núm", "Temp", "Titulo", "Fecha"};
		Object[][] rowData;
		
		public EpisodioTable(){
			rowData = new Object[0][columnNames.length];			
		}
		
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return rowData.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return rowData[row][col];
		}
		
		public void setValueAt(Object obj, int row, int col) {
			rowData[row][col] = obj;
			fireTableCellUpdated(row, col);
		}
		public boolean isCellEditable(int row, int col) {
			return false;
		}
		

		public void refresh(List<Episodio> episodios){
			Episodio epi = null;
			rowData = new Object[episodios.size()][columnNames.length];
			for(int row = 0; row < episodios.size(); row++){
				epi = episodios.get(row);
				setValueAt(epi.getNumEpisodio(),  row, 0);
				setValueAt(epi.getNumTemporada(), row, 1);
				setValueAt(epi.getTitulo(), 	  row, 2);
				setValueAt(epi.getFechaInicio(),  row, 3);
			}		
				
			fireTableDataChanged();
		}
		
		public String getTituloEpisodio(int row) {
			return (String) getValueAt(row,2 );
		}
}
	

