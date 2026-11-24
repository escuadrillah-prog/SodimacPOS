PROYECTO AUTOSERVICIO POS HOMECENTER SODIMAC - 100% WINDOWS & NETBEANS 8.0+
==============================================================================

DISENADO EXCLUSIVAMENTE PARA WINDOWS (SIN RED HAT / SIN LINUX):
- Compatible con todas las versiones de Windows: Windows 7, Windows POSReady 7, Windows 8 / 8.1,
  Windows 10 y Windows 11 (32 y 64 bits).
- Proyecto nativo de NetBeans (compatible con NetBeans 8.0, 8.1, 8.2 y Apache NetBeans 11 a 23):
  Solo abra la carpeta POS_Sodimac_Completo_Java8_v8 directamente desde Archivo -> Abrir Proyecto.
- Tambien incluye EJECUTAR_EN_WINDOWS.bat para abrirlo con doble clic directo en Windows.

FUNCIONALIDADES REALES DE PUNTO DE VENTA (POS) EN WINDOWS:
1. Persistencia Real en Carpeta Local de Windows (%USERPROFILE%\POS_Sodimac_Data):
   - catalogo_productos.csv: Catalogo editable de productos (se crea automaticamente con 12 productos reales).
   - ventas_realizadas.csv: Registro historico real de cada venta completada (Folio, Fecha/Hora, RUT Socio,
     Metodo de Pago, Subtotal, Descuento, Total, Pagado, Vuelto).
   - boletas\BOLETA_000001.txt: Genera una boleta electronica real por cada compra aprobada y permite
     imprimirla directamente en la impresora predeterminada de Windows (o verla en pantalla).
2. Integracion Nativa con Windows Narrator (Narrador de Windows) + Voz SAPI.SpVoice en Espanol:
   - Usa la Java Accessibility API de Windows para que el Narrador de Windows (Win + Ctrl + Enter)
     lea botones, campos, tablas, montos y cambios de pantalla en tiempo real.
   - Incorpora motor directo Windows SAPI.SpVoice (via WScript/CScript UTF-16LE) con deteccion automatica
     de voces en espanol de Windows (Helena, Sabina, Raul, Laura, Pablo) para locucion inmediata.
3. Operacion Real de Caja Autoservicio:
   - Validacion matematica real de RUT chileno (Modulo 11 con digito verificador 0-9 o K).
   - Escaneo de codigo de barras por teclado/pistola USB (1001 al 1012 + Enter) o busqueda en catalogo.
   - Gestion real de inventario/stock descontado al vender, edicion de cantidades, anulacion de lineas,
     aplicacion de cupones reales (SODIMAC1000, DESC2000, HOGAR15) y pago en efectivo con calculo real
     de billetes ($1.000, $2.000, $5.000, $10.000, $20.000) y vuelto.
