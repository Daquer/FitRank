package br.com.fitrank.util;


public final class ConstantesFitRank {
	
	//Períodos
	public static final String NOITE = "N";
	public static final String DIA = "D";
	public static final String SEMANA = "S";
	public static final String MES = "M";
	public static final String ANO = "A";
	public static final String SEMPRE = "T";
	
	//Modalidades
	public static final String MODALIDADE_CAMINHADA = "W";
	public static final String MODALIDADE_BICICLETA = "B";
	public static final String MODALIDADE_CORRIDA = "R";
	public static final String MODALIDADE_TUDO = "A";
	
	public static final String MODALIDADE_PADRAO = "R";
	public static final String MODO_PADRAO = "D";                                           
	public static final String PERIODO_PADRAO = "M";
	
	public static final String MODALIDADE_PRIMEIRO_LOGIN = "A";
	public static final String MODO_PRIMEIRO_LOGIN = "D";                                           
	public static final String PERIODO_PRIMEIRO_LOGIN = "M";
	
	public static final String CHAR_SIM = "S";
	public static final String CHAR_NAO = "N";
	
	public static final int TAMANHO_PADRAO_RANKING = 3000;
	
	
	//Aplicativos
	public static final String ID_APP_NIKE = "84697719333";
	public static final String ID_APP_RUNTASTIC = "162918433202";
	public static final String ID_APP_RUNKEEPER = "62572192129";
	public static final String ID_APP_ENDOMONDO = "202423869273";
	public static final String ID_APP_STRAVA = "284597785309";
	public static final String ID_APP_MAPMYRUN = "43211574282";
	public static final String ID_APP_MAPMYRIDE = "43656497834";
	public static final String ID_APP_MAPMYFITNESS= "44829295357";
	public static final String ID_APP_MAPMYWALK= "34785190853";
	
	public static final String ID_APP_RUNTASTIC_MOUNTAIN_BIKE = "402248583177025";
	public static final String ID_APP_RUNTASTIC_ROAD_BIKE = "475072582538239";
	
	public static final String ID_APP_FITRANK = "749336888463283";
	
	//Modos
	public static final String VELOCIDADE_MEDIA = "V";
	public static final String DISTANCIA = "D";
	public static final String QUANTIDADE = "Q";
	
	//Informações pessoais
	public static final String SEXO_FEMININO = "F";
	public static final String SEXO_MASCULINO = "M";
	public static final String FACEBOOK_MALE_GENDER = "male";
	public static final String FACEBOOK_FEMALE_GENDER = "female";
	
	//Limites
	public static final int LIMITE_CORRIDAS_REALIZADAS_POR_DIA = 2;
	public static final int LIMITE_MAX_RECUPERA_FB = 99999;
	public static final int LIMITE_ATUALIZACAO_USUARIOS = 25;
	public static final int LIMITE_MINUTOS_ATUALIZACAO_USUARIOS = 30;

	//Outros
	public static final int INT_RESULTADO_INVALIDO = -1;
	public static final float MILHA_EM_KM = 1.60934F;
	
	//Constantes que devem ser iniciadas com o valor do arquivo .properties | Valores são definidos na classe JDBCFactory
	public static String app_secret = "";
}