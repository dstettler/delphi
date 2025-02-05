unit EchoNumber;

interface

type
  TEcho = class
  private
    FNumber: Integer;
  public
    constructor Create(ANumber: Integer);
    destructor Destroy; override; // Destructor
    function Echo: Integer; // Method to return the number and print it
  end;

implementation

constructor TEcho.Create(ANumber: Integer);
begin
  FNumber := ANumber;
end;

destructor TEcho.Destroy;
begin
  // Any necessary cleanup code can go here.
  inherited Destroy;
end;

function TEcho.Echo: Integer;
begin
  Writeln('Echoed Number: ', FNumber);
  Result := FNumber;  // Return the number that is echoed
end;

end.
