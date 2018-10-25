#include <LiquidCrystal.h>//libreria para el arduino para el uso del LCD
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);//posicion de los poines
//hemos declarado la librería para la LCD y los pines por donde le va a entrar la información.

byte PIN_SENSOR = A0;// posicion del pin A0 para el LM35
int dato_serial = 0;// para los datos de entrada serie
float C;
// Declaramos la variable del tipo tiempo hora
long timepassed;
int time;
int temp;
int h = 9;//horas 
int m; //minutos
int s;//segundos
int flag;
int TIME;//tiempo
const int hs = 9;
const int ms = 45;
int state1;
int state2;

void setup() {
  lcd.begin(16, 2);// Configuramos las filas y las columnas del LCD en este caso 16 columnas y 2 filas
  Serial.begin(9600);// Configuración monitor serie
  Serial.setTimeout(50);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(13, OUTPUT);
  time = millis();
}

float centi(){//METODO CENTIGRADOS
  int dato;
  float c;
  dato = analogRead(A0);//el dato del pin A0 
  c = (500.0 * dato) / 1023;//funcion para obtener temperatura
  return (c);//retorno
}

void loop() {
  int i = 0;//contador
  timepassed = millis();// para milisegundos

  if (i == 0) {//llama a la primera funcion para mostrar la hora
    Relog();
    time = millis();
    i++;
    lcd.clear();//limpia pantalla
    s = s + 10;
  }

  if (i == 1) {//llama a la segunda funcion para mostrar la el mensaje
    Mensajes();
    delay(5000);// Esperamos 5 segundos igual a 5000 milisegundos
    time = millis();//timepo en transiccion
    i++;
    lcd.clear();
  }

  if (i == 2) {//llama a la tercera funcion para mostrar la temperatura
    temperatura();
    delay(5000);// Esperamos 5 segundos igual a 5000 milisegundos
    time = millis();//timepo en transiccion
    i++;
    lcd.clear();// Limpiamos la pantalla
  }
  i = 1;
}

void lectura_dato (void ) {
  dato_serial = Serial.read();// lee el byte de entrada:
}

void Mensajes() {
  lcd.clear();// Limpiamos la pantalla
  String text = Serial.readString();//lee el serial para la comunicacion del mensaje
  String line1 = text.substring(0, 16);//posicion de la primera fila
  String line2 = text.substring(16, 32);//posicion de la segunda fila

  if (text.length() > 0) {//si el tamaño del texto es igual a 0 se va a comadando rrespecto al la linea
    lcd.setCursor(0, 0);// Situamos el cursor en la columna 0 fila 0
    lcd.print("                ");// Escribimos
    lcd.setCursor(0, 1);
    lcd.print("                ");// Escribimos
    lcd.clear();
  }

  lcd.setCursor(0, 0);// Situamos el cursor en la columna 0 fila 0
  lcd.print(line1);//imprime en la primera fila
  lcd.setCursor(0, 1);// Situamos el cursor en la columna 0 fila 1
  lcd.print(line2);//imprime en la segunda fila
}

void temperatura() {
  for (int i = 0; i < 5; i++) {//siclo para temperatura
    lcd.clear();// Limpiamos la pantalla
    lcd.begin(16, 2);//iniciamos 
    lcd.print("C=");
    lcd.setCursor(0, 1);
    lcd.print("Temperatura");//mensaje
    float Centigrados = centi();//llama al metodo centi

    lcd.setCursor(2, 0);
    lcd.print(Centigrados);
  }
}

void Relog() {//metodo reloj
  for (int i = 0; i < 5; i++) {
    lcd.setCursor(0, 0);// Situamos el cursor en la columna 0 fila 0
    s = s + 1;
    lcd.print("HORA:");//imprimer el formato de hora
    lcd.print(h);
    lcd.print(":");
    lcd.print(m);
    lcd.print(":");
    lcd.print(s);
    if (flag < 12)lcd.print("AM");
    if (flag == 12)lcd.print("PM");
    if (flag > 12)lcd.print("PM");
    if (flag == 24)flag = 0;
    delay(1000);// Esperamos 2 segundos igual a 2000 milisegundos
    lcd.clear();// Limpiamos la pantalla
    if (s > 60) {
      s = 0;
      m = m + 1;
    }
    if (m == 60)
    {
      m = 0;
      h = h + 1;
      flag = flag + 1;
    }
    if (h == 13)
    {
      h = 1;
    }

    //-------Time
    // setting-------//
    state1 = digitalRead(hs);
    if (state1 == 1)
    {
      h = h + 1;
      flag = flag + 1;
      if (flag < 12)lcd.print("AM");//formato de hora
      if (flag == 12)lcd.print("PM");
      if (flag > 12)lcd.print("PM");
      if (flag == 24)flag = 0;
      if (h == 13)h = 1;
    }
    state2 = digitalRead(ms);
    if (state2 == 1) {
      s = 0;
      m = m + 1;
    }
  }
}
