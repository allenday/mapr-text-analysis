#!/usr/bin/perl
use strict;
use GraphViz;

my $g = GraphViz->new(layout=>'neato');

while ( my $line = <> ) {
  chomp $line;
  my ( $s, $t ) = split /\t/, $line;

  #$g->add_node( $s );
  #$g->add_node( $t );
  next if $s == $t;
  $g->add_edge( $s => $t );
}
print $g->as_png();

__END__

$g->add_node('London');
$g->add_node('Paris', label => 'City of\nlurve');
$g->add_node('New York');

$g->add_edge('London' => 'Paris');
$g->add_edge('London' => 'New York', label => 'Far');
$g->add_edge('Paris' => 'London');

print $g->as_png;

