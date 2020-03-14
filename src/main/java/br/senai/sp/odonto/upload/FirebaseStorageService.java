package br.senai.sp.odonto.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {
	
	//Realizar a checagem da conexão
	//@PostConstruct -> Assim que construir a aplicação, execute esse método
	@PostConstruct
	public void init() throws IOException{
		
		//Verifica se existe alguma aplicação Firebase em uso
		//Se não houver, iniciar serviço
		if(FirebaseApp.getApps().isEmpty()) {
			
			//Classe que manipula os arquivos texto
			InputStream dadosAutenticacaoFirebase = FirebaseStorageService.class.getResourceAsStream("/firebase-account-key.json");
			
			FirebaseOptions options = new FirebaseOptions
					.Builder()
					.setCredentials(
							 GoogleCredentials
							.fromStream(dadosAutenticacaoFirebase))
					.setStorageBucket("odonto-spring-api.appspot.com")
					.setDatabaseUrl("https://odonto-spring-api.firebaseio.com")
					.build();
			
			//Initializando o app com as opções
			FirebaseApp.initializeApp(options);
		}
	}
	
	public String upload(UploadInput uploadInput) {
		
		Bucket bucket = StorageClient.getInstance().bucket();
		
		//Pegando o atributo base64 do objeto uploadInput
		//e decodificando para seu formato de arquivo original em bits
		byte[] bytes = Base64.getDecoder().decode(uploadInput.getBase64());
		
		//Coletando o nome do arquivo
		String filename = uploadInput.getFilename();
		
		//Coletando o tipo de protocolo
		String mimeType = uploadInput.getMimeType();
		
		//Criação do arquivo no firebase passando o nome, o arquivo em bits e o tipo
		Blob arquivo = bucket.create(filename, bytes, mimeType);
		
		//Informando pelo Acl (Access Control List) que todos os usuários podem fazer leituras
		arquivo.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		
		//Formata os espaços com %s por argumentos
		return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), filename);
		
	}

}
