package baldeep.quiztagwriter;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String fileName;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    boolean writeMode;
    IntentFilter intentFileters[];
    Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> textFileNames = new ArrayList<>();
        final HashMap<String, String> filePathMap = new HashMap<>();

        String sdPath = System.getenv("SECONDARY_STORAGE");
        File sdCard = new File(sdPath);
        if(sdCard.exists()){
            String folderPath = sdPath + "/Quiz Tag";
            File filesFolder = new File(folderPath);
            if(filesFolder.exists()) {
                File[] contents = filesFolder.listFiles();
                if (contents != null) {
                    for (File f : contents) {
                        if (f.getName().endsWith(".txt")) {
                            textFileNames.add(f.getName());
                            filePathMap.put(f.getName(), f.getAbsolutePath());
                        }
                    }
                } else {
                    System.out.println("Contents empty");
                }
            } else {
                System.out.println("FOLDER NOT FOUND");
            }
        } else {
            System.out.println("SD CARD NOT FOUND");
        }


        String[] fileNames = new String[textFileNames.size()];
        fileNames = textFileNames.toArray(fileNames);

        final ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileNames);

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fileName = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        Button write_btn = (Button) findViewById(R.id.button);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName != null) {
                    String fileAsString = "";
                    try {
                        Log.d("File Path", filePathMap.get(fileName));
                        BufferedReader br = new BufferedReader(new FileReader(filePathMap.get(fileName)));

                        String line = "";
                        while((line = br.readLine()) != null){
                            Log.d("Line", line);
                            fileAsString = fileAsString + "\n" + line;
                            Log.d("File As String", fileAsString);
                        }
                        Log.d("fileAsString", fileAsString);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e("FileNotFoundException", "FileNotFoundException");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("IOException", "IOException");
                    }
                    Toast.makeText(MainActivity.this, "File Name" + fileName, Toast.LENGTH_SHORT).show();

                }
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        intentFileters = new IntentFilter[] { tagDetected };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
        super.onNewIntent(intent);
    }

    private void writeTag(String text, Tag tag){
        NdefRecord record = createRecord(text);
        NdefRecord[] records = new NdefRecord[]{record};
        NdefMessage message = new NdefMessage(records);

        // Check the tag is in Ndef format
        Ndef ndef = Ndef.get(tag);
        if(ndef != null){
            try {
                ndef.connect(); // establish connection
                ndef.writeNdefMessage(message);
                Toast.makeText(this, "Ndef Write successful", Toast.LENGTH_SHORT).show();
                ndef.close(); //close connection
            } catch (IOException e) {
                Toast.makeText(this, "Failed to write, tag may have moved", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (FormatException e) {
                Toast.makeText(this, "Tag is of invalid format", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            // If it's not in Ndef Format, format it if possible
            NdefFormatable formattable = NdefFormatable.get(tag);
            if(formattable != null){
                try {
                    formattable.connect();
                    formattable.format(message);
                    Toast.makeText(this, "Ndef Format and write successful", Toast.LENGTH_SHORT).show();
                    formattable.close();
                }  catch (IOException e) {
                    Toast.makeText(this, "Failed to write, tag may have moved", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(this, "Tag is of invalid format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Invalid Tag format", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private NdefRecord createRecord(String text){
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        int    textLength = textBytes.length;

        byte[] langBytes  = new byte[0];
        try {
            langBytes = lang.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int    langLength = langBytes.length;

        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

        return recordNFC;
    }

}
