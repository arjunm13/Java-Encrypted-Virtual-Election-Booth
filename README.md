# Java-Encrypted-Virtual-Election-Booth
Researched and applied network security techniques to design and implement a secure virtual election application where only verified users can place votes


The voting system follows the following steps:
1. The voter will send their credentials to the Central Legitimization Agency (CLA) and
request a validation number.
2. The CLA then checks if the user credentials are valid. If they are valid, the CLA will
provide a validation number if the user has not voted. The CLA keeps a list of generated
validation numbers and the recipients of those validation numbers, which is used to check
if a user has already voted.
3. The voter then creates a message which is made up of the validation number received from
the CLA, the voter credentials and the vote. This message is then sent to the Central
Tabulating Facility (CTF).
4. The CTF will compare the user credentials and validation number with the CLA to make
sure the user is valid. If they are valid, the CTF will record the users vote.
