
import speech_recognition as sr

r=sr.Recognizer()
with sr.Microphone() as source:
	r.dynamic_energy_threshold=False
	print('speak out')
	audio=r.listen(source)
	print("timedout")
print('speak out2')
audio2=r.listen(source)
print("timedout")
try:
	print("text :"+r.recognize_google(audio,language='en-IN'))
	print("text :"+r.recognize_google(audio2,language='en-IN'))
except:
	pass