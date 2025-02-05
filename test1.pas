program EchoNumberTest;

uses
  EchoNumber;

var
  EchoObj: TEcho;

begin
  EchoObj := TEcho.Create(42); // Creating an object with number 42
  EchoObj.Echo; // This will output: Echoed Number: 42
  EchoObj.Free; // Don't forget to free the object to avoid memory leaks!
end.
