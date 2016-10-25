package abd.pr1.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Serie;

public class EpisodioMapper extends AbstractMapper<Episodio, Integer>  {


	private static final String[] EPISODIO_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] EPISODIO_COLUMN_NAMES = new String[] { "id", "numEpisodio", "numTemporada", "sinopsis", "fechaInicio", "titulo", "idSerie"};
	private static final String EPISODIO_TABLE_NAME = "episodio";

	
	
	public EpisodioMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return EPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return EPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Episodio object) {
		return new Object[] { 
				object.getId(), 	
				object.getNumEpisodio(),
				object.getNumTemporada(),
				object.getSinopsis(),
				object.getFechaInicio(),
				object.getTitulo(),
				object.getIdSerie()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return EPISODIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected Episodio buildObject(ResultSet rs) throws SQLException {
		Integer id 			 = rs.getInt("id");
		Integer numEpisodio  = rs.getInt("numEpisodio");
		Integer numTemporada = rs.getInt("numTemporada");
		String sinopsis 	 = rs.getString("sinopsis");
		Date fechaInicio     = rs.getDate("fechaInicio");
		String titulo 		 = rs.getString("titulo");
		Integer idSerie 	 = rs.getInt("idSerie");
		
		return new Episodio(id, numEpisodio, numTemporada, sinopsis, fechaInicio, titulo, idSerie);
	}

	@Override
	protected Integer getKey(Episodio object) {
		return object.getId();
	}
	
	public List<Episodio> buscarEpisodios(Object nombreSerie){
		int idSerie = obtenerIdSerie(nombreSerie);
	
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("idSerie", Operator.EQ, idSerie);
			
		List<Episodio> episodios = findByConditions(conditions);
		
		return episodios;
	}
	
	private int obtenerIdSerie(Object nombreSerie) {
		SerieMapper serieMapper =  new SerieMapper(ds);
		Serie serie = serieMapper.buscarUnaSerie(nombreSerie);

		return serie.getId(); 
	}

	public Episodio buscarPrimerEpisodio(Object nombreSerie){
		Episodio episodio = null;
		int idSerie = obtenerIdSerie(nombreSerie);	
		
		QueryCondition[] conditions = new QueryCondition[3];
		conditions[0] = new QueryCondition("idSerie", 	   Operator.EQ, idSerie);
		conditions[1] = new QueryCondition("numTemporada", Operator.EQ, "1");
		conditions[2] = new QueryCondition("numEpisodio",  Operator.EQ, "1");
		
		List<Episodio> listEpisodio = findByConditions(conditions);
		
		if(!listEpisodio.isEmpty()){
			episodio = listEpisodio.get(0);
	
		}
		
		return episodio;
	}
	
	public Episodio buscarEpisodio(Object nombreEpisodio){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("titulo", Operator.LIKE, nombreEpisodio);
		Episodio episodio = null;
		List<Episodio> episodios = findByConditions(conditions);
		if(!episodios.isEmpty())
			episodio = episodios.get(0);
		return episodio;
	}

	
	

}
