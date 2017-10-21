package defpackage;

import java.util.HashMap;
import java.util.Locale;

public final class k {
    private static k a;
    private HashMap b = new HashMap();

    private k() {
        String displayLanguage = Locale.getDefault().getDisplayLanguage();
        if (displayLanguage.compareTo("français") == 0) {
            this.b.put("Confirmation", "Confirmation");
            this.b.put("CameraUnavailable", "L'application n'est pas parvenue à accéder à votre caméra");
            this.b.put("Hold your card here", "Maintenez votre carte à l'intérieur du cadre");
            this.b.put("manual entry", "Saisie manuelle");
            this.b.put("Expiration date", "Date d'expiration");
            this.b.put("Cryptogram", "Cryptogramme");
            this.b.put("Number", "Nº de carte");
            this.b.put("Wrong Token", "Scan momentanément indisponible");
            this.b.put("Security Message", "Procédé sécurisé. Aucune image ou donnée de carte n'est stockée.");
        } else if (displayLanguage.compareTo("español") == 0 || displayLanguage.compareTo("català") == 0) {
            this.b.put("Confirmation", "Confirmar");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Mantenga la tarjeta dentro del marco");
            this.b.put("manual entry", "Entrada manual");
            this.b.put("Expiration date", "Fecha de vencimiento");
            this.b.put("Cryptogram", "Código de seguridad");
            this.b.put("Number", "N° de tarjeta");
            this.b.put("Wrong Token", "Escanear momentáneamente indisponible");
            this.b.put("Security Message", "La imagen de la tarjeta y los datos no se almacenarán.");
        } else if (displayLanguage.compareTo("Deutsch") == 0) {
            this.b.put("Confirmation", "Bestätigen");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Karte in den Rahmen halten");
            this.b.put("manual entry", "Manuelle Eingabe");
            this.b.put("Expiration date", "Ablaufdatum");
            this.b.put("Cryptogram", "Kartenprüfnummer");
            this.b.put("Number", "Kartennummer");
            this.b.put("Wrong Token", "Scannen vorübergehend nicht verfügbar ist");
            this.b.put("Security Message", "Die Karte Bilder und Daten werden nicht gespeichert.");
        } else if (displayLanguage.compareTo("português") == 0) {
            this.b.put("Confirmation", "Confirmar");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Segure o cartão dentro do quadro.");
            this.b.put("manual entry", "Digitar");
            this.b.put("Expiration date", "Data de validade");
            this.b.put("Cryptogram", "Código de segurança");
            this.b.put("Number", "N° de cartão");
            this.b.put("Wrong Token", "A digitalizaçao nao esta disponivel no momento");
            this.b.put("Security Message", "Processo seguro. Nenhuma foto ou informação será arquivada.");
        } else if (displayLanguage.compareTo("italiano") == 0) {
            this.b.put("Confirmation", "Confermare");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Mantenere la carta nel quadro.");
            this.b.put("manual entry", "Inserimento manuale");
            this.b.put("Expiration date", "Data di scadenza");
            this.b.put("Cryptogram", "Codice di sicurezza");
            this.b.put("Number", "N° della carta");
            this.b.put("Wrong Token", "Scansione momentaneamente indisponibile");
            this.b.put("Security Message", "L'immagine della carta e dati non saranno memorizzati.");
        } else if (displayLanguage.compareTo("svenska") == 0) {
            this.b.put("Confirmation", "Bekräfta");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Håll ditt betalkort här");
            this.b.put("manual entry", "Manuell inmatning");
            this.b.put("Expiration date", "Utgångsdatum");
            this.b.put("Cryptogram", "CVC");
            this.b.put("Number", "Kortnummer");
            this.b.put("Wrong Token", "Scan momentant otillgänglig");
            this.b.put("Security Message", "Kortet bild och data kommer inte att lagras.");
        } else if (displayLanguage.compareTo("dansk") == 0) {
            this.b.put("Confirmation", "Bekræft");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Hold dit kort inden for rammen");
            this.b.put("manual entry", "Manuel indtastning");
            this.b.put("Expiration date", "Udløbsdato");
            this.b.put("Cryptogram", "CVC");
            this.b.put("Number", "Kortnummer");
            this.b.put("Wrong Token", "Scan momentant utilgængelig");
            this.b.put("Security Message", "Sikker proces. Kortets billede og data vil ikke blive gemt.");
        } else if (displayLanguage.compareTo("Nederlands") == 0) {
            this.b.put("Confirmation", "Bevestigen");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Houd uw kaart in het frame.");
            this.b.put("manual entry", "Handmatige invoer");
            this.b.put("Expiration date", "Vervaldatum");
            this.b.put("Cryptogram", "CVC");
            this.b.put("Number", "Kaartnummer");
            this.b.put("Wrong Token", "Scannen tijdelijk niet voorhanden");
            this.b.put("Security Message", "De kaart afbeelding en gegevens worden niet opgeslagen.");
        } else if (displayLanguage.compareTo("Türkçe") == 0) {
            this.b.put("Confirmation", "Onaylamak");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Kartınızı çerçevenin içerisine yerleştiriniz");
            this.b.put("manual entry", "Elle giriş");
            this.b.put("Expiration date", "Son kullanma tarihi");
            this.b.put("Cryptogram", "Kart Güvenlik Kodu");
            this.b.put("Number", "Kart numarası");
            this.b.put("Wrong Token", "An kullanılamaz Tarama");
            this.b.put("Security Message", "Güvenli işlem. Kart resmi ve bilgileri saklanmayacaktır.");
        } else {
            this.b.put("Confirmation", "Confirm");
            this.b.put("CameraUnavailable", "The application couldn't access to your camera");
            this.b.put("Hold your card here", "Hold your card within the frame");
            this.b.put("manual entry", "Manual entry");
            this.b.put("Expiration date", "Expiration date");
            this.b.put("Cryptogram", "3/4-digit security code");
            this.b.put("Number", "Card number");
            this.b.put("Wrong Token", "Scan momentarily unavailable");
            this.b.put("Security Message", "Secure process. No card picture or data will be stored");
        }
    }

    public static k a() {
        if (a == null) {
            a = new k();
        }
        return a;
    }

    public final String a(String str) {
        return this.b.get(str) != null ? (String) this.b.get(str) : str;
    }
}
