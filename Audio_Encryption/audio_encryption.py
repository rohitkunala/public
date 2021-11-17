
#Process - 1 , Directly encrypting audio file

from cryptography.fernet import Fernet

#encryption 
def encrypt(original_audio_file_path ,encrypted_file_path , key):

    fernet=Fernet(key)

    with open(original_audio_file_path,'rb') as file: #location of voice file to be encrypted
        originalaudio=file.read()

    encrypted=fernet.encrypt(originalaudio)

    with open(encrypted_file_path,'wb') as encrypted_file: #location of newly created voice file after encryption
        encrypted_file.write(encrypted)

    #the encryption audio file is not playable , since all the audio content is encrypted , software apps cannot read the actual data 

#decryption
def decrypt(encrypted_file_path ,decrypted_file_path , key):

    fernet=Fernet(key)

    with open(encrypted_file_path,'rb') as enc_file: #location of newly created voice file to be decrypted
        encrypted=enc_file.read()

    decrypted =fernet.decrypt(encrypted)

    with open(decrypted_file_path,'wb') as dec_file: #location of newly created voice file after decrypted
        dec_file.write(decrypted)


key=Fernet.generate_key()

#print(key)

encrypt('/home/rohit/Desktop/data/test.wav', '/home/rohit/Desktop/data/encrypted.wav', key)

decrypt('/home/rohit/Desktop/data/encrypted.wav', '/home/rohit/Desktop/data/decrypted.wav', key)


'''
    with open('key.key','wb') as filekey:
        filekey.write(key)

    with open('key.key','rb') as filekey:
        key=filekey.read()
'''


'''
#Process - 2 , Converting speech to text 

import speech_recognition as sr
import pyttsx3 as pt
eng=pt.init()
r=sr.Recognizer()
with sr.Microphone(device_index=0) as m:
	r.dynamic_energy_threshold=False
	r.adjust_for_ambient_noise(m,1)
	print('speak out')
	#print(sr.Microphone.list_microphone_names())	
	eng.say("listening")
	audio=r.listen(m,timeout=5)
	print("timedout")
	'''
print('speak out2')
audio2=r.listen(source,timeout=0.5)
print("timedout")'''
try:
	print("text :"+r.recognize_sphinx(audio))
	#print("text :"+r.recognize_google(audio2s,language='en-IN'))
except:
	print('error')

'''